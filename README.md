# Terminator

Android app for browsing Sharif course data and building a weekly class plan.

## What It Does

- Loads bundled course data from JSON files in `app/src/main/res/raw`.
- Groups courses by department.
- Shows each course in a RecyclerView list with course number, units, capacity, schedule, and exam time.
- Filters courses by name, instructor, course number, or course id.
- Lets the user add courses to a selected weekly chart.
- Rejects duplicate course numbers, class conflicts, and exam conflicts.
- Saves selected courses locally so they are restored after the app restarts.

## Setup

Install:

- Android Studio
- JDK 17
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
- Selected course persistence: `SelectedCourseStorage` with SharedPreferences
- Course display text and time parsing: `Course`, including one-meeting and two-meeting classes

The app is local-only. It does not call a server and stores selected course ids in local SharedPreferences.
