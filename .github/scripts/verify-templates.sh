#!/bin/bash

set -euo pipefail

# Configuration
CLI_JAR_URL="https://github.com/fluxzero-io/fluxzero-cli/releases/latest/download/fluxzero-cli.jar"
CLI_JAR="fluxzero-cli.jar"
TEST_DIR="cli-verification-test"
JAVA_PACKAGE="com.example.test"
GROUP_ID="com.example"
ARTIFACT_ID="test-project"

log_info() {
    echo "INFO: $1"
}

log_success() {
    echo "SUCCESS: $1"
}

log_warning() {
    echo "WARNING: $1"
}

log_error() {
    echo "ERROR: $1"
}

cleanup() {
    log_info "Cleaning up test directory..."
    rm -rf "$TEST_DIR" "$CLI_JAR" 2>/dev/null || true
}

# Trap to ensure cleanup on exit
trap cleanup EXIT

get_template_info() {
    local project_dir="$1"
    # For local templates, the template name should match the directory name
    echo "$project_dir"
}

main() {
    local project_dir="${1:-}"
    
    if [ -z "$project_dir" ]; then
        log_error "Usage: $0 <project-directory>"
        log_error "Example: $0 flux-basic-kotlin"
        exit 1
    fi
    
    # Get the corresponding template name
    template=$(get_template_info "$project_dir")
    project_name="test-$project_dir"
    
    log_info "Starting FluxZero CLI template verification for $template (from $project_dir)"
    
    # Verify the source template directory exists
    # The script is called from the repo root, so check directly
    if [ ! -d "$project_dir" ]; then
        log_error "Template directory $project_dir does not exist"
        exit 1
    fi
    
    # Create test directory
    mkdir -p "$TEST_DIR"
    cd "$TEST_DIR"
    
    # Download CLI JAR
    log_info "Downloading FluxZero CLI from $CLI_JAR_URL"
    if ! curl -L -s -o "$CLI_JAR" "$CLI_JAR_URL"; then
        log_error "Failed to download CLI JAR"
        exit 1
    fi
    
    # Verify Java is available
    if ! java -version >/dev/null 2>&1; then
        log_error "Java not found. Java is required to run the CLI JAR"
        exit 1
    fi
    
    # Test CLI version
    log_info "Testing CLI version"
    if ! java -jar "$CLI_JAR" version >/dev/null 2>&1; then
        log_error "Failed to run CLI version command"
        exit 1
    fi
    
    log_info "Testing template: $template -> $project_name"
    
    # Generate project from local template using --template-path
    template_path=".."  # Point to the repository root containing the template directories
    if java -jar "$CLI_JAR" init \
        --template-path "$template_path" \
        --template "$template" \
        --name "$project_name" \
        --package "$JAVA_PACKAGE" \
        --group-id "$GROUP_ID" \
        --artifact-id "$ARTIFACT_ID" \
        --description "Test project for $template template" \
        --build gradle \
        --git >/dev/null 2>&1; then
        log_success "Generated project $project_name from template $template"
    else
        log_error "Failed to generate project $project_name from template $template"
        exit 1
    fi
    
    # Verify project structure
    if [ -d "$project_name" ]; then
        log_success "Project directory $project_name created"
        
        # Check for expected files
        cd "$project_name"
        
        expected_files=("src" "gradlew")
        for file in "${expected_files[@]}"; do
            if [ -e "$file" ]; then
                log_success "Found expected file/directory: $file"
            else
                log_warning "Missing expected file/directory: $file"
            fi
        done
        
        # Check for build file (either Gradle or Maven)
        if [ -f "build.gradle.kts" ] || [ -f "build.gradle" ]; then
            log_success "Found Gradle build file"
        elif [ -f "pom.xml" ]; then
            log_success "Found Maven build file"
        else
            log_warning "No build file found (build.gradle.kts, build.gradle, or pom.xml)"
        fi
        
        # Verify basic project structure looks correct
        log_info "Verifying generated project structure"
        
        # Check that source directory has expected Java/Kotlin files
        if [ -d "src/main/java" ] || [ -d "src/main/kotlin" ]; then
            log_success "Found source directories"
        else
            log_warning "No src/main/java or src/main/kotlin directories found"
        fi
        
        cd ..
    else
        log_error "Project directory $project_name not created"
        exit 1
    fi
    
    log_success "CLI template verification completed successfully for $template"
}

# Run main function
main "$@"