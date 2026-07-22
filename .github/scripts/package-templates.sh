#!/usr/bin/env bash
set -euo pipefail

repo_root=$(cd "$(dirname "$0")/../.." && pwd)
output=${1:-"$repo_root/build/templates.zip"}
staging=$(mktemp -d)
trap 'rm -rf "$staging"' EXIT

templates=(flux-basic-java flux-basic-kotlin)
project_instructions=(AGENTS.md CLAUDE.md GEMINI.md)

for template in "${templates[@]}"; do
  source_dir="$repo_root/$template"
  [[ -d "$source_dir" ]] || { echo "Missing template: $template" >&2; exit 1; }

  for instruction in "${project_instructions[@]}"; do
    [[ -f "$source_dir/$instruction" ]] || { echo "Missing $template/$instruction" >&2; exit 1; }
  done

  if find "$source_dir" -path '*/.fluxzero/agents*' -print -quit | grep -q .; then
    echo "Repository-local Fluxzero manuals are not allowed in template $template" >&2
    exit 1
  fi

  while IFS= read -r -d '' file; do
    mkdir -p "$staging/$(dirname "$file")"
    cp -p "$repo_root/$file" "$staging/$file"
  done < <(git -C "$repo_root" ls-files -z -- "$template")
done

mkdir -p "$(dirname "$output")"
rm -f "$output"
(cd "$staging" && zip -qr "$output" .)
echo "Created $output"
