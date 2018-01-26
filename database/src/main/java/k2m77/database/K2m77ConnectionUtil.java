package k2m77.database;

public abstract class K2m77ConnectionUtil {

	private K2m77ConnectionUtil() {
		// nothing;
	}

	public static abstract class K {
		public static String SELECT = "SELECT";
		public static String DROP = "DROP";
		public static String CREATE = "CREATE";
		
		public static String TABLE = "TABLE";
	}

	public static abstract class S {
		public static String KB = " ";
		public static String SY = "\"";
		public static String DY = "'";
		public static String QK = "(";
		public static String HK = ")";
		public static String DH = ",";
		public static String FH = ";";
	}
}
