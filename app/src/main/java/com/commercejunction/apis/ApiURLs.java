package com.commercejunction.apis;

import androidx.navigation.PopUpToBuilder;

public class ApiURLs {
    public static final String BASE_URL="http://www.thecommercejunction.somee.com";

    public static final String REGISTRATION_URL = "/Student.svc/json/Registration";

    public static final String LOGIN_URL = "/Student.svc/json/Login";

    public static final String MEDIUM_LIST_URL = "/Lists.svc/json/MediumList/";

    public static final String STANDARD_LIST_URL = "/Lists.svc/json/StandardList/{MEDIUMID}";

    public static final String SUBJECT_LIST_URL = "/Lists.svc/json/SubjectList/{STANDARDID}";

    public static final String CHAPTERS_LIST_URL = "/Lists.svc/json/ChapterList/{SUBJECTID}";

    public static final String VIDEO_LIST_URL = "/Lists.svc/json/VideoList/{CHAPTERID}";

    public static final String PDF_LIST_URL = "/Lists.svc/json/VideoList/{CHAPTERID}";

    public static final String CHANGE_PASSWORD_URL = "/Student.svc/json/ChangePassword/{STUDENTID}";

    public static final String EDIT_PROFILE_URL = "/Student.svc/json/EditProfile/{STUDENTID}";



}