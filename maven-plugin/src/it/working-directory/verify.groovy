static void testReportXml(File fromDirectory) {
    File report = new File(fromDirectory, 'test-working-directory/reports/report.xml')

    assert report.file
}

testReportXml(basedir)
