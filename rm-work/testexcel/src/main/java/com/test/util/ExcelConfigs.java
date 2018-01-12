package com.test.util;

public final class ExcelConfigs {

    private int noOfRowsToSkip;
    private boolean isNullAsDefault;
    private String sheetName;
    private String colorToSkipRows;

    private ExcelConfigs() {
        super();
    }

    private ExcelConfigs setNoOfRowsToSkip(int noOfRowsToSkip) {
        this.noOfRowsToSkip = noOfRowsToSkip;
        return this;
    }

    private ExcelConfigs setIsNullAsDefault(boolean isNullAsDefault) {
        this.isNullAsDefault = isNullAsDefault;
        return this;
    }

    private ExcelConfigs setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public String getSheetName() {
        return sheetName;
    }

    public boolean getIsNullAsDefault() {
        return isNullAsDefault;
    }

    /**
     * the number  row to be skipped in top
     *
     * @return first n rows to be skipped
     */
    public int getNoOfRowsToSkip() {
        return noOfRowsToSkip;
    }

    public String getColorToSkipRows() {
		return colorToSkipRows;
	}

	private ExcelConfigs setColorToSkipRows(String colorToSkipRows) {
		this.colorToSkipRows = colorToSkipRows;
		return this;
	}

	public static class ExcelSettingsBuilder {

        private int noOfRowsToSkip = 1;
        private boolean isNullAsDefault = false;
        private String sheetName;
        private String colorToSkipRows;

        private ExcelSettingsBuilder() {
        }

        private ExcelSettingsBuilder(int noOfRowsToSkip) {
            this.noOfRowsToSkip = noOfRowsToSkip;
        }

        public ExcelConfigs build() {
            return new ExcelConfigs()
                    .setNoOfRowsToSkip(noOfRowsToSkip)
                    .setIsNullAsDefault(isNullAsDefault)
                    .setSheetName(sheetName)
                    .setColorToSkipRows(colorToSkipRows);
        }

        public static ExcelSettingsBuilder settings() {
            return new ExcelSettingsBuilder();
        }      

        /**
         * To configure default value as null
         *
         * @param isNullAsDefault boolean
         * @return this
         */
        public ExcelSettingsBuilder isNullAsDefault(boolean isNullAsDefault) {
            this.isNullAsDefault = isNullAsDefault;
            return this;
        }

        /**
         * Number of rows to be skipped
         *
         * @param noOfRowsToSkip number
         * @return this
         */
        public ExcelSettingsBuilder noOfRowsToSkip(int noOfRowsToSkip) {
            this.noOfRowsToSkip = noOfRowsToSkip;
            return this;
        }

        /**
         * color code to skip the rows/cell
         *
         * @param color code String
         * @return this
         */
        public ExcelSettingsBuilder colorToSkipRows(String color) {
            this.colorToSkipRows = color;
            return this;
        }

        /**
         * Sheet name to read
         *
         * @param sheet name String
         * @return this
         */
        public ExcelSettingsBuilder sheetName(String sheetName) {
            this.sheetName = sheetName;
            return this;
        }
        /**
         * Number of rows to be skipped in top. default 1
         *
         * @param noOfRowsToSkip ignored row number
         * @return builder 
         */
        public static ExcelSettingsBuilder settings(int noOfRowsToSkip) {
            return new ExcelSettingsBuilder(noOfRowsToSkip);
        }

    }
}
