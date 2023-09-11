Contributing to UltimateCore
==========================
The InfernalSuite team are really glad you're willing to contribute to UltimateCore.
We operate a very open and lenient PR policy, but there are still some guidelines you
can follow to make the approval process go more smoothly.

## Use a Personal Fork and not an Organization

We will routinely modify your PR, whether it's a quick rebase or to take care
of any minor issues we might have. Often, it's better for us to solve these
problems for you than make you go back and forth trying to fix them yourself.

Unfortunately, if you use an organization for your PR, it prevents us from
modifying it. This requires us to manually merge your PR, resulting in us
closing the PR instead of marking it as merged.

We much prefer to have PRs show as merged, so please do not use repositories
on organizations for PRs.

See <https://github.com/isaacs/github/issues/1681> for more information on the
issue.

## Prerequisites

- JDK17 Development Environment
- Suitable Java IDE (We recommend IntelliJ IDEA)
- Git and an understanding of Git principles
- An understanding of Gradle
- A good understanding of Java and relevant best practices. (We're happy to work with you on PRs, but we will close PRs that submit poor quality code)

## Making Changes

- Fork the repository and clone your fork locally
- Create a new branch for your changes. Ideally you should base your branch off the `develop` branch
- Make your changes and commit them locally. Use <u>[this guide](https://chris.beams.io/posts/git-commit/)</u> to help you write good commit messages
- Ensure your changes are tested and the project builds successfully
- Push your changes to your fork
- Open a PR against the `develop` branch of our repository
- Wait for a member of the InfernalSuite team to review your PR
- You may be asked to make changes to your PR. If so, make the changes and push them to your fork. The PR will be updated automatically
- If your PR is related to an existing feature request or bug report in GitHub issues, please link it in the PR description
- Likewise, if an existing post exists in the uc-support forum channel on our discord server, adding a comment to the post linking the PR is a big help

## Discussion

- The uc-dev channel on our discord is a great place to start for new contributors. Feel free to ask questions about the project or your PR
- If you're looking for something to work on, check GitHub Issues for the project, or the uc-support channel for anything tagged with `bug` or `feature request`
- If you're looking to work on something that isn't in GitHub Issues or the uc-support channel, please open a new issue or use the uc-dev channel and discuss it with us before you start work on it
- If you're a regular contributor, we have a uc-contrib channel on our discord to streamline communication. Please ask us for the `UC Contributor` role if you're interested!

## Formatting

We generally follow the usual Java style (aka. Oracle style), or what is programmed
into most IDEs and formatters by default. There are a few notes, however:
- It is fine to go over 80 lines as long as it doesn't hurt readability.  
  There are exceptions, especially in Spigot-related files
- When in doubt or the code around your change is in a clearly different style,
  use the same style as the surrounding code.

Code comments are good but don't over-comment - code should be fairly self-explanatory and you should consider refactoring if it isn't.<br>
However more complicated methods may require comments to ensure maintainability

We would greatly prefer methods to be JavaDoc'd where possible, especially public methods - and this is a requirement for anything that is part of the public API.
