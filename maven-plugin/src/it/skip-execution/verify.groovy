static void testReportXml(File fromDirectory) {
    File report = new File(fromDirectory, 'reports/report.xml')

    assert !report.exists()
}

testReportXml(basedir)
