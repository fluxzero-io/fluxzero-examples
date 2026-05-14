# Fluxzero Examples

A collection of starter projects demonstrating the capabilities of the [Fluxzero SDK](https://fluxzero.io). These examples serve as both learning resources and source templates for the Fluxzero CLI (`fz init`).

These projects are designed to become the first commit of real customer applications. Many builders start from them with AI assistance and only light technical background, so the templates favor clear, explicit, single-project setup over build-system abstraction. A human developer or agent can always evolve a generated project into a multi-module build, add shared build logic, or introduce stricter tooling later.

## 🚀 Available Examples

### [`flux-basic-java`](./flux-basic-java/)
Basic Java project demonstrating fundamental Fluxzero concepts. Shows minimal setup and core SDK usage patterns.

**Features:**
- Basic project structure
- Essential Fluxzero components
- Ready-to-run configuration
- Deliberately simple Java-first starter setup

### [`flux-basic-kotlin`](./flux-basic-kotlin/)
Kotlin equivalent of the basic Java example with Kotlin-specific enhancements.

**Features:**
- JDK 25+ support
- IntelliJ IDEA integration with live templates
- Clear single-project Gradle and Maven setup
- HTTP client environment for local API calls

### [`gamerental`](./gamerental/)
Comprehensive game rental system showcasing advanced Fluxzero features and production-ready patterns.

**Features:**
- 🎯 **Zero-boilerplate command handling** with event sourcing
- 🔍 **Live search & queries** using document store
- ⚖️ **Role-based access control** with JSR-380 validation
- 📦 **Dynamic Dead Letter Queue (DLQ)** with error recovery
- 🌐 **Clean Web API** with type-safe handlers
- 🔄 **WebSocket support** for real-time updates
- 🧪 **Comprehensive testing** with Fluxzero test framework

## 🛠️ Using Examples with Fluxzero CLI

These examples are automatically packaged with the Fluxzero CLI and can be used to scaffold new projects:

```bash
# List available templates
fz templates list

# Create a new project from an example
fz init --template flux-kotlin-single --name my-app

# Interactive template selection
fz init
```

For more CLI usage information, see the [Fluxzero CLI documentation](https://fluxzero.io/docs).

## 🧭 Template Design Principles

- **Start simple**: generated applications should be easy to understand before they are easy to abstract.
- **Keep build files readable**: versions and dependencies should usually appear where customers and agents edit them.
- **Prefer boring defaults**: avoid optional tooling that can break first builds unless it provides clear starter value.
- **Stay extensible**: templates should not prevent teams from adding multi-module structure, shared Gradle conventions, stricter linting, or organization-specific build policy later.
- **Protect the Java path**: `flux-basic-java` is the most important starter template and should remain especially small, reliable, and obvious.

## 🏃‍♂️ Running Examples

Each example includes its own README with specific setup and running instructions. Generally:

1. **Prerequisites**: JDK 25+ required for all examples
2. **Build**: Use provided Gradle or Maven configurations (both supported)
3. **Run**: Examples include IntelliJ run configurations or can be started via command line
4. **Test**: Comprehensive test suites demonstrate Fluxzero testing capabilities

## 🤝 Contributing Third-Party Examples

We welcome community contributions! To submit your example:

1. **Fork** this repository
2. **Create** your example in a new directory following existing patterns
3. **Include** a comprehensive README documenting your example
4. **Add tests** demonstrating your example's functionality
5. **Submit** a pull request

### Requirements for New Examples

- **Self-contained**: Must be runnable out of the box
- **Well-documented**: Clear README explaining purpose and usage
- **Tested**: Include comprehensive tests
- **Multi-build support**: Must support both Maven and Gradle build systems
- **Template configuration**: Must include `refactor.yaml` to instruct the Fluxzero CLI how to customize the template for users
- **Supporting files**: Include useful HTTP request files, run configs, and local tooling files when they improve the generated starter project
- **License agreement**: By contributing, you agree that your example will be published under the same license as this repository

### Example Structure

```
your-example/
├── README.md                 # Comprehensive documentation
├── refactor.yaml            # Template customization instructions for CLI
├── build.gradle.kts         # Gradle build configuration
├── pom.xml                  # Maven build configuration
├── src/
│   ├── main/               # Source code
│   └── test/               # Tests and optional HTTP request examples
└── http-client.env.json    # Optional local HTTP client environment
```

## 📋 Template Integration

When the Fluxzero CLI builds, it automatically downloads and packages these examples as templates. The build process:

1. Downloads the latest examples from this repository
2. Packages them for CLI distribution
3. Makes them available via `fz templates list` and `fz init`

Template features include:
- **Package name replacement** for Java/Kotlin projects
- **File customization** based on user input and `refactor.yaml` configuration
- **Interactive configuration** during project creation
- **Build system selection** between Maven and Gradle

## 📖 Documentation

- [Fluxzero SDK Documentation](https://fluxzero.io/docs)
- [Fluxzero CLI Repository](https://github.com/fluxzero-io/fluxzero-cli)

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

**By contributing to this repository, you agree that your contributions will be licensed under the same license as this project.**

---

**Happy coding with Fluxzero! 🚀**
