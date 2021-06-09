# DialogFragmentFun
All the perils of a simple non-cancellable dialog fragment in Android

This simple project demonstrates how to create a single instance dialog fragment and why that is useful. It also demonstrates the perils of a non-cancellable dialogfragment which is shown at the start of an asynchronous operation and dismissed at the end of said operation. Issues can occur when while showing the dialog, a process death or view model death can cause the dialog to be shown infinitely.
