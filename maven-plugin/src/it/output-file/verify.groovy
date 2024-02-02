static void testReportXml(File fromDirectory) {
    File log = new File(fromDirectory, 'target/logs/simple-run.log')
    File report = new File(fromDirectory, 'reports/report.xml')

    assert log.file: 'log'
    assert report.file: 'report'
}

testReportXml(basedir)
