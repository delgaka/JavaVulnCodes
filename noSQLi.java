 /* Here use MongoDB as target NoSQL DB */
String userInput = "Brooklyn";

/* First ensure that the input do no contains any special characters 
for the current NoSQL DB call API,
here they are: ' " \ ; { } $
*/
//Avoid regexp this time in order to made validation code 
//more easy to read and understand...
ArrayList<String> specialCharsList = new ArrayList<String>() { {
    add("'");
    add("\"");
    add("\\");
    add(";");
    add("{");
    add("}");
    add("$");
} };
specialCharsList.forEach(specChar -> Assert.assertFalse(userInput.contains(specChar)));
//Add also a check on input max size
Assert.assertTrue(userInput.length() <= 50);

/* Then perform query on database using API to build expression */
//Connect to the local MongoDB instance
try(MongoClient mongoClient = new MongoClient()){
    MongoDatabase db = mongoClient.getDatabase("test");
    //Use API query builder to create call expression
    //Create expression
    Bson expression = eq("borough", userInput);
    //Perform call
    FindIterable<org.bson.Document> restaurants = db.getCollection("restaurants").find(expression);
    //Verify result consistency
    restaurants.forEach(new Block<org.bson.Document>() {
        @Override
        public void apply(final org.bson.Document doc) {
            String restBorough = (String)doc.get("borough");
            Assert.assertTrue("Brooklyn".equals(restBorough));
        }
    });
}
