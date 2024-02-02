static void testReportXml(File fromDirectory) {
    File report = new File(fromDirectory, 'target/ijhttp-reports/report.xml')

    assert report.file
}

testReportXml(basedir)
