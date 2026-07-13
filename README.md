# Terminator

Android app for browsing Sharif course data and building a weekly class plan.

## What It Does

- Loads bundled course data from JSON files in `app/src/main/res/raw`.
- Groups courses by department.
- Shows each course in a RecyclerView list.
- Lets the user add courses to a selected weekly chart.
- Rejects duplicate course numbers and time conflicts.

## Setup

Install:

- Android Studio
- JDK 11
- Android SDK Platform 32

Open the repo in Android Studio, or build from PowerShell:

```powershell
cd "C:\Users\imanm\Downloads\GitHub\Mobile-Programming-HWs\HW1"
.\gradlew.bat :app:assembleDebug
```

The debug APK is created at:

```text
app\build\outputs\apk\debug\app-debug.apk
```

## Run

Use Android Studio, or install on a connected device:

```powershell
adb devices
.\gradlew.bat :app:installDebug
```

## Implementation Notes

- Main screen: `MainActivity`
- Course list UI: `CourseListFragment`
- Weekly chart UI: `WeaklyChartFragment`
- Selected course state: `SelectedCourse`
- Course time parsing: `Course`, including one-meeting and two-meeting classes

The app is local-only. It does not call a server or store data outside memory.
