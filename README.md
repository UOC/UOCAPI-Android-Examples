UOCAPI-Android-Examples
=======================

Here you will find some examples of how to call the UOC API. Please note that these are just examples, they are not designed to be perfect or to be released for commercial use.

There is exactly one example for every call to the API as well as one old example project (a little bigger than then rest) and an example of an android library for UOC API. All projects are named like the API call they represent.

If you were to look at our iOS examples, you will note that most examples are named differently. That's because of the length of the path for some projects files (in windows this pathname has a limited length), so we were forced to rename the projects to make their path shorter. I will explain now how we named them to make it easier for you to identify them:

* <b>Cal: </b>Calendar.
* <b>Cl: </b>Classrooms.
* <b>B: </b>Boards.
* <b>F: </b>Folders.
* <b>M at the start of a project name: </b>Mail.
* <b>M: </b>Messages.
* <b>MM: </b>MailMessages.
* <b>P at the start of a project name: </b>People.
* <b>P: </b>Profile.
* <b>S: </b>Subjects.
* <b>U: </b>User.
* <b>I: </b>Id.
* <b>G: </b>Get.
* <b>P at the end of a project name: </b>Post or Put.

For example, following this list, the example of a Get to .../people/{id}/profile/current is called PIP_CurrentG.

Note that al examples use our example of an Android library, "OpenAPILibrary", except representative calls of each example (the one that gives his name to the example).

