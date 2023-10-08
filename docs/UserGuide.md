---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# CM-23 User Guide

ClassManager-2023 (CM-23) is a **desktop app for managing your students' contacts,
optimized for use via a Command Line Interface** (CLI) while still having the benefits of a
Graphical User Interface (GUI). If you can type fast, CM-23 can get your contact
management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `classmanager.jar` from [here](https://github.com/AY2324S1-CS2103T-T11-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your ClassManager.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar classmanager.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all student details.
     
   * `add f/John l/Doe e/e0123456@u.nus.edu c/11 s/A0123456X` : Adds a student named `John Doe` to the Class Manager.

   * `delete 3` : Deletes the 3rd student detail shown in the current list.

   * `clear` : Deletes all student details.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME c/CLASS_NUMBER`, `c/CLASS_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a person: `add`

This feature in Class Manager 2023 is a robust tool that empowers CS2103T TAs to add new student information according
to the specified parameters (FIRST NAME, LAST NAME, SCHOOL EMAIL, CLASS NUMBER, and STUDENT NUMBER, TAG[Optional]).

Format: `add f/{FIRST NAME} l/{LAST NAME} e/{EMAIL} c/{CLASS NUMBER} s/{STUDENT NUMBER} [t/{TAG}]…​`

* **ALL** the fields must be provided.
* The FIRST NAME and LAST NAME fields are case-sensitive.
* SCHOOL EMAIL and STUDENT NUMBER needs to be unique

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add f/ Ngee Yong l/ Lim e/ e0930481@u.nus.edu c/ 11 s/ A0249112A t/Best Student t/Possible TA`
* `add f/ Boh Shin l/ Yeo e/ e09301234@u.nus.edu c/ 5 s/ A0126362A`

### Listing all student details : `list`

Shows a list of all student details in the class manager.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS]​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower` Edits the name of the 2nd person to be `Betsy Crower`.

### Tagging a student: `tag`

Tags the existing student in the address book.

Format: `tag STUDENT NUMBER [t/TAG]…​`

* Tags the student with the specified `STUDENT NUMBER`. 
* When editing tags, the existing tags of the student will be removed i.e adding of tags is not cumulative.
* You can remove all the student’s tags by typing `t/` without
  specifying any tags after it.

Examples:
* `tag A1234567N t/smart t/shy t/funny` tags the specified student with the smart, shy and funny.
* `tag A1234567N t/` clear the tags from the specified student.

### Lookup students: `lookup`

**TL;DR:** Lookup student details containing any of the given fields.

This feature in Class Manager 2023 is a robust tool that empowers CS2103T TAs to 
efficiently search for and access student information based on specific criteria. 
This feature offers both broad and granular search capabilities, enabling TAs to
list all students from a particular class or narrow down their search by providing
one or more lookup parameters.

Format: `lookup [c/CLASS_NUMBER}] [l/LAST_NAME] [e/EMAIL] [s/STUDENT_NUMBER] [t/TAG]`

<box type="warning" seamless>

**Caution:**
_At least one_ of the optional fields must be provided. `lookup` alone is not allowed.
</box>

* The command is case-insensitive. e.g `hans` will match `Hans`
* Only full words will be matched e.g. `Han` will not match `Hans`
* This command can only take one word per field. e.g. `lookup c/11 12` is _**not** allowed_.
  * Taking multiple words per field _coming soon_ in future versions.

Examples:

* `lookup l/li` returns `Alex Li`, `David Li`<br>
  ![result for 'lookup l/ li'](images/lookupLastNameResult.png) </br></br>
* `lookup c/111` return all students in class number 111<br>
  ![result for 'lookup c/ 111'](images/lookupClassResult.png)

### Deleting a student : `delete`

Deletes the specific student.

Format: `delete s/STUDENT_NUMBER`

* The STUDENT NUMBER must be valid and exist. 

Examples:
* `delete s/A0249112A`

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

ClassManager data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ClassManager data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, ClassManager will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.
</box>

### Loading the data file: `load`

Load student information from an existing JSON file. Copy the JSON file to be loaded into the data folder. The data in the JSON file will be loaded into the app. The file also becomes the new default save file.

Format: `load f/FILE_NAME`
* File name does not need to include .json extension.
* File name is case-insensitive

Examples:
* `load export-v1` loads the export-v1.json file in the data folder.

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ClassManager home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete s/STUDENT_NUMBER`<br> e.g., `delete s/A0249112A`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Tag**    | `tag STUDENT NUMBER [t/TAG]…​` <br> e.g `tag A0123456N t/smart t/shy`
**Lookup** | `lookup [c/CLASS_NUMBER] [l/LAST_NAME] [e/EMAIL] [s/STUDENT_NUMBER] [t/TAG]` <br> e.g `lookup c/111`
**List**   | `list`
**Help**   | `help`
**Load**   | `load f/FILE_NAME`
