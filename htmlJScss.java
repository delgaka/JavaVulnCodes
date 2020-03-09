/*
INPUT WAY: Receive data from user
Here it's recommended to use strict input validation using whitelist approach.
In fact, you ensure that only allowed characters are part of the input received.
*/

String userInput = "You user login is owasp-user01";

/* First we check that the value contains only expected character*/
Assert.assertTrue(Pattern.matches("[a-zA-Z0-9\\s\\-]{1,50}", userInput));

/* If the first check pass then ensure that potential dangerous character 
that we have allowed for business requirement are not used in a dangerous way.
For example here we have allowed the character '-', and, this can 
be used in SQL injection so, we
ensure that this character is not used is a continuous form.
Use the API COMMONS LANG v3 to help in String analysis...
*/
Assert.assertEquals(0, StringUtils.countMatches(userInput.replace(" ", ""), "--"));

/*
OUTPUT WAY: Send data to user
Here we escape + sanitize any data sent to user
Use the OWASP Java HTML Sanitizer API to handle sanitizing
Use the OWASP Java Encoder API to handle HTML tag encoding (escaping)
*/

String outputToUser = "You <p>user login</p> is <strong>owasp-user01</strong>";
outputToUser += "<script>alert(22);</script><img src='#' onload='javascript:alert(23);'>";

/* Create a sanitizing policy that only allow tag '<p>' and '<strong>'*/
PolicyFactory policy = new HtmlPolicyBuilder().allowElements("p", "strong").toFactory();

/* Sanitize the output that will be sent to user*/
String safeOutput = policy.sanitize(outputToUser);

/* Encode HTML Tag*/
safeOutput = Encode.forHtml(safeOutput);
String finalSafeOutputExpected = "You <p>user login</p> is <strong>owasp-user01</strong>";
Assert.assertEquals(finalSafeOutputExpected, safeOutput);
