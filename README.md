# Fluxzero Project Templates (moved)

The maintained Java and Kotlin project templates now live in the
[Fluxzero CLI repository](https://github.com/fluxzero-io/fluxzero-cli/tree/main/templates/src/main/template-sources).
Their packaging, validation, and release lifecycle are part of the CLI build, so a released CLI and its bundled
templates always belong together.

Use the latest Fluxzero CLI to generate a project:

```bash
fz init --template flux-basic-java --name my-app --package com.example.myapp --build maven
```

Use `flux-basic-kotlin` for Kotlin and select either `maven` or `gradle` for `--build`. Update the CLI when you want a
newer template version; an installed CLI deliberately keeps using the templates bundled with that CLI release.

Open template changes and issues in
[`fluxzero-io/fluxzero-cli`](https://github.com/fluxzero-io/fluxzero-cli). This repository and its existing releases are
retained as historical records; it no longer publishes standalone template archives.

## License

The historical template source is licensed under the Apache License 2.0. See [LICENSE](LICENSE).
