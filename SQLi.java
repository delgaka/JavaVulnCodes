/*No DB framework used here in order to show the real use of 
  Prepared Statement from Java API*/
/*Open connection with H2 database and use it*/
Class.forName("org.h2.Driver");
String jdbcUrl = "jdbc:h2:file:" + new File(".").getAbsolutePath() + "/target/db";
try (Connection con = DriverManager.getConnection(jdbcUrl)) {

    /* Sample A: Select data using Prepared Statement*/
    String query = "select * from color where friendly_name = ?";
    List<String> colors = new ArrayList<>();
    try (PreparedStatement pStatement = con.prepareStatement(query)) {
        pStatement.setString(1, "yellow");
        try (ResultSet rSet = pStatement.executeQuery()) {
            while (rSet.next()) {
                colors.add(rSet.getString(1));
            }
        }
    }
    Assert.assertEquals(1, colors.size());
    Assert.assertTrue(colors.contains("yellow"));

    /* Sample B: Insert data using Prepared Statement*/
    query = "insert into color(friendly_name, red, green, blue) values(?, ?, ?, ?)";
    int insertedRecordCount;
    try (PreparedStatement pStatement = con.prepareStatement(query)) {
        pStatement.setString(1, "orange");
        pStatement.setInt(2, 239);
        pStatement.setInt(3, 125);
        pStatement.setInt(4, 11);
        insertedRecordCount = pStatement.executeUpdate();
    }
    Assert.assertEquals(1, insertedRecordCount);

   /* Sample C: Update data using Prepared Statement*/
    query = "update color set blue = ? where friendly_name = ?";
    int updatedRecordCount;
    try (PreparedStatement pStatement = con.prepareStatement(query)) {
        pStatement.setInt(1, 10);
        pStatement.setString(2, "orange");
        updatedRecordCount = pStatement.executeUpdate();
    }
    Assert.assertEquals(1, updatedRecordCount);

   /* Sample D: Delete data using Prepared Statement*/
    query = "delete from color where friendly_name = ?";
    int deletedRecordCount;
    try (PreparedStatement pStatement = con.prepareStatement(query)) {
        pStatement.setString(1, "orange");
        deletedRecordCount = pStatement.executeUpdate();
    }
    Assert.assertEquals(1, deletedRecordCount);

}
