package com.cmccarthy.common.utils.extent;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporter {

    public static final ExtentReports extent = GetReporter.extent;

    private static class GetReporter {

        public static final ExtentReports extent = new ExtentReports();
        private static final ExtentSparkReporter spark = new ExtentSparkReporter("target/spark-reports/spark-report.html");

        static {
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setReportName("Test Report");
            spark.config().setDocumentTitle("Automation");
            spark.config().setEncoding("UTF-8");
            spark.config().setTimelineEnabled(true);
            spark.config().enableOfflineMode(false);
            spark.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
            spark.config().setCss(".col-md-3{-webkit-box-flex:0;-ms-flex:0 0 20%;flex:0 0 20%;max-width:20%}.text-skip {color: #e68A00 !important;}");
            extent.attachReporter(spark);
            extent.setSystemInfo("Environment", "");
            extent.setSystemInfo("Platform", "");
            extent.setSystemInfo("Device version", "");
            extent.setAnalysisStrategy(AnalysisStrategy.TEST);
        }
    }
}