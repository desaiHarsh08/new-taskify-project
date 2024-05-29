package com.taskify.app;

public class AppConstants {

    public static final String JWT_HEADER = "Authorization";

    public static final String JWT_SECRET_KEY = "ZVB7wG9c5W0Rk3sA6pI2eV8fD4lNjM7ox4Ct6yGh11mZs3oQw8rVb2aTc5fVB7wG9c5W0Rk3sA6pICt6yGht6yGh11mZs3oZVB7wG9c5W0Rk3sA6pI2eV8fD4lNjM7ox4Ct6yGh11mZsc5fVB7wG9c5W0Rk3sA6pIC";

    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;

    public static final String ARGUMENT_EXCEPTION_MESSAGE = "TOKEN ERROR: ILLEGAL ARGUMENT OR MALFORMED JWT EXCEPTION";

    public static final String EXPIRED_JWT_TOKEN_MESSAGE = "TOKEN ERROR: EXPIRED JWT TOKEN EXCEPTION";

    public static final String USERS_TABLE = "users";

    public static final String DEPARTMENTS_TABLE = "departments";

    public static final String ROLES_TABLE = "roles";

    public static final String TASK_TEMPLATE_TABLE = "task_templates";

    public static final String FUNCTION_TEMPLATE_TABLE = "function_templates";

    public static final String FUNCTION_METADATA_TEMPLATE_TABLE = "function_metadata_templates";

    public static final String METAFIELD_TEMPLATE_TABLE = "metafield_templates";

    public static final String TASK_TABLE = "tasks";

    public static final String FUNCTION_TABLE = "functions";

    public static final String FUNCTION_METADATA_TABLE = "functions_metadata";

    public static final String META_FIELD_TABLE = "metafields";

    public static final String REFRESH_TOKEN_TABLE = "refresh_tokens";

    public static final String ACTIVITY_LOGS_TABLE = "activity_logs";

    public static final String CUSTOMER_TABLE = "customer_table";


}
