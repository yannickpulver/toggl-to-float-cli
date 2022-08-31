# Toggl-2-float

Handy Kotlin Console application that transfers your float projects to Toggl and your time entries from Toggl to
float.

### 🪄 Features / Commands:

`t2f fetchProjects` - Adds projects from float as projects in Toggl

`t2f addTime DATE` - Adds time entries from Toggl to float. Date format: `2022-06-24`

### 🧑‍💻 Usage

Run `t2f fetchProjects` to get all the projects from float to Toggl. When doing time entries, select one of these
projects. You may add `tags` with your float Activity name, otherwise the default activity will be taken (you will get
asked to choose one.).

If you want to report time not by project but by issue, add the issue number in front of the description
like: `12345 something`.

In order to submit time entries, you can call `t2f addTime 2022-07-12` (replace the date with the date you want to
submit).



### 🚧 How to install:

1. Take the latest `toggl2float.jar` from [Releases](https://github.com/yannickpulver/toggl-to-float/releases) and place it on your Desktop (mac):
2. Open Terminal
3. Copy Paste following block into Terminal + Click Enter:

```
mkdir -p ~/Toggl && mv ~/Desktop/toggl2float.jar ~/Toggl/toggl2float.jar
echo "alias t2f='java -jar ~/Toggl/toggl2float.jar'" >> ~/.zshrc
source ~/.zshrc
```

4. Now you should be able to run `t2f`, `t2f fetchProjects` and `t2f addTime DATE`.


### ⚠️ Java Error during execution?

Then you need to do a few extra steps to install java. 
1. Install homebrew (skip if already have it):

```
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

2. Run these commands.

```
brew install java
echo 'export PATH="/opt/homebrew/opt/openjdk/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
   ```

Now `t2f` should be available. 