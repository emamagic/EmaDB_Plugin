# EmaDBPlugin

EmaDBPlugin is an IntelliJ IDEA plugin that provides static code analysis for [EmaDB ORM](https://github.com/emamagic/EmaDB) annotations. With this plugin, developers working with [EmaDB](https://github.com/emamagic/EmaDB) benefit from real-time feedback and suggestions, helping them catch configuration and annotation issues early in the development process. This plugin supports your [EmaDB](https://github.com/emamagic/EmaDB) library, making sure it’s used effectively and consistently in your Java projects.

## Features

- **Annotation Validation**: Automatically verifies [EmaDB](https://github.com/emamagic/EmaDB) annotations, helping you catch mistakes before compilation.
- **Code Inspections**: Checks for mandatory fields and correct usage of [EmaDB](https://github.com/emamagic/EmaDB) annotations like `@Entity`, `@Id`, `@Transient`, and `@Unique`.
- **Quick Fix Suggestions**: Provides automatic suggestions to correct annotation usage, missing configurations, or misnamed fields.
- **Ease of Use**: Seamlessly integrates into IntelliJ, helping you focus on code quality.

---

## Installation

1. **Download the Plugin**  
   Download the latest version of [ema-db-plugin-1.0.zip](./releases/ema-db-plugin-1.0.zip).

2. **Install the Plugin in IntelliJ**  
   1. Open IntelliJ IDEA.
   2. Go to **Settings** > **Plugins**.
   3. Click **Install Plugin from Disk...**  
   4. Choose the downloaded `ema-db-plugin-1.0.zip` file and click **OK**.

3. **Enable [EmaDB](https://github.com/emamagic/EmaDB) Inspections**  
   1. After installing, ensure that [EmaDB](https://github.com/emamagic/EmaDB) inspections are enabled:
      - Go to **Settings** > **Editor** > **Inspections**.
      - Check the [EmaDB](https://github.com/emamagic/EmaDB) inspections to activate them.

---

## Usage

Once installed, EmaDBPlugin automatically analyzes classes using [EmaDB](https://github.com/emamagic/EmaDB) annotations, providing feedback in real-time. Here’s what you can expect:

- **Real-time Annotation Checks**: Validates required annotations and their usage in your project.
- **Error Marking**: Highlights incorrect or missing annotations directly in the code editor.
- **Quick Fixes**: If a class is missing an essential annotation or configuration, simply use the IntelliJ quick fix suggestions to resolve issues quickly.

---

## Example of Inspections

If you annotate a class as `@Entity` without an `@Id` field, EmaDBPlugin will display an error, guiding you to add the necessary `@Id` annotation. Similar checks apply to `@Config` classes and other EmaDB-specific annotations.

---

## Contributing

If you’re interested in contributing to EmaDBPlugin, feel free to open an issue or submit a pull request. We welcome any improvements, new feature ideas, or feedback on existing functionality!

---

## License

EmaDBPlugin is licensed under the MIT License. See the [LICENSE](./LICENSE) file for more details.

---

Happy coding with EmaDBPlugin! 🎉 Let the plugin handle the nitty-gritty, so you can focus on building awesome things with [EmaDB](https://github.com/emamagic/EmaDB). If anything goes amiss, remember: code is like a puzzle—sometimes you just need a plugin to find the missing piece. 😉
