EntityManager entityManager = null;
try {
    /* Get a ref on EntityManager to access DB */
    entityManager = Persistence.createEntityManagerFactory("testJPA").createEntityManager();

    /* Define parameterized query prototype using named parameter to enhance readability */
    String queryPrototype = "select c from Color c where c.friendlyName = :colorName";

    /* Create the query, set the named parameter and execute the query */
    Query queryObject = entityManager.createQuery(queryPrototype);
    Color c = (Color) queryObject.setParameter("colorName", "yellow").getSingleResult();

    /* Ensure that the object obtained is the right one */
    Assert.assertNotNull(c);
    Assert.assertEquals(c.getFriendlyName(), "yellow");
    Assert.assertEquals(c.getRed(), 213);
    Assert.assertEquals(c.getGreen(), 242);
    Assert.assertEquals(c.getBlue(), 26);
} finally {
    if (entityManager != null && entityManager.isOpen()) {
        entityManager.close();
    }
}
