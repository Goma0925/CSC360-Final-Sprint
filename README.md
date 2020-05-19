# Solo Sprint - Amon Otsuki

- Tasks Completed:
    - I completed all three tasks. The unit tests for each feature can be found in the following test files in "solesprints" directory in the test. Tests from some of the other tests from previous sprints might not work due to the change I made in the last sprint.
    1. Comment Feature
        - Test in TestCommentView.
        - This test will select one of the sections, 1) attempts to create a comment and test the cancel button, 2) creates several comments, and 3) deletes the comments to see if they are properly deleted.

    2. Business Plan Comparison
        - Test in TestComparisonView.
        - This test will select two business plans in the selector view and open the comparison view to display the differences in the business plan. It checks if the differences are highlighted in red if the business plan models have different data.


    3. Preview Feature (Original)
        - Test in TestPreview
        - This test will 1) renders the preview page with a test business plan data, 2) jumps to the view page and edit one of the business plan's sections, and 3) goes back to the preview page to see if the content change is reflected in the preview.

- Additional notes:

    - To run as an actual program, you can go to src/main/java/main/MainVPView and run this file. It will take you to the login page. The login information is:
        - Name: X
        - PW: 1
    - There are also manual tests to test each of the features manually, which I used for part of the development along with the automated tests. They can be found in src/test/java/manualtests.
