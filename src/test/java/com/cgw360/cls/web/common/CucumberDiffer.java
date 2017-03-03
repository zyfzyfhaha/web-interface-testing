package com.cgw360.cls.web.common;

import java.util.List;

import cucumber.api.DataTable;
import cucumber.api.Scenario;

/**
 * this is a helper class for print the cucumber datatable.
 * @author zyf
 */
public class CucumberDiffer {
    /** Scenario class to print the message to report. */
    private Scenario scenario;
    /**
     * print table size.
     */
    private int reportSize = 50;

    /**
     * public constructor.
     * @param scenario
     *            use to print the message to report
     */
    public CucumberDiffer(final Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * print the result with List<Object> format.
     * @param prefix
     *            is report prefix
     * @param otherTable
     *            is values.
     */
    private void printDataTable(final String prefix, final DataTable otherTable) {
        scenario.write(formatOutput(prefix, otherTable.raw()));
    }

    /**
     * print the result with List<Object> format.
     * @param prefix
     *            is report prefix
     * @param tableContent
     *            is values.
     */
    public void printDataTable(final String prefix,
            final List<List<String>> tableContent) {
        scenario.write(formatOutput(prefix, tableContent));
    }

    /**
     * print the result with List<Object> format.
     * @param expected
     *            is expected datatable
     * @param actual
     *            is actual datatable.
     */
    public void diff(final DataTable expected, final DataTable actual) {
        printDataTable("Expected Data:", expected);
        printDataTable("Actual Data:", actual);
        expected.diff(actual);
    }

    /**
     * print the result with List<Object> format.
     * @param expected
     *            is expected datatable
     * @param actual
     *            is actual datatable.
     * @param reportSize
     *            is report size
     */
    public void diff(final DataTable expected, final DataTable actual,
            final int reportSize) {
        this.reportSize = reportSize;
        printDataTable("Expected Data:", expected);
        printDataTable("Actual Data:", actual);
        expected.diff(actual);
    }

    /**
     * this function is used to format testcase report.
     * @param prefix
     *            prefix name
     * @param rowList
     *            need print data
     * @return html code
     */
    public String formatOutput(final String prefix,
            final List<List<String>> rowList) {

        StringBuilder tableHtml = new StringBuilder("");
        tableHtml
                .append("<span style='background-color: #C5D88A'>")
                .append(prefix)
                .append("</span>")
              //  .append("<span style='background-color: #C5D88A;font-align:right'>")
              //  .append("Total:").append(rowList.size())
             //   .append("</span>")
                .append("<table class='data-table' style = 'background-color: #C5D88A'>");
        for (int i = 0; i < rowList.size(); i++) {
            if (i >= reportSize) {
                tableHtml.append("<tr>");
                tableHtml.append("<td>").append("......").append("</td>");
                tableHtml.append("</tr>");
                break;
            }
            List<String> row = rowList.get(i);
            tableHtml.append("<tr>");
            for (String column : row) {
                tableHtml.append("<td>").append(column).append("</td>");
            }
            tableHtml.append("</tr>");
        }
        tableHtml.append("</table>");

        return tableHtml.toString();

    }
    
}
