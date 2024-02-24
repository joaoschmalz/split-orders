# Split orders

Imagine that you and a few colleagues want to share a snack that you are ordering via iFood/Uber Eats using your smartphone. It's easy to find out how much your colleague should pay you when there is no discount or delivery fee, but when these factors come into question, simply dividing the amount in half may not be the fairest.

It is fair that delivery and discount are proportional to the value of the items each person purchased. In order to facilitate this calculation we have Split Order

The application makes a proportional calculation of how much each colleague should pay you, and then generates a QRCode following the Central Bank's Pix rules, allowing you to make the payment directly through your bank's app, regardless of which one it is.

## Project Variables

To ensure the project works as expected, configure the below variables in the config.properties file

`pixKey`
`keyType`

{c:red}Project will not be executed if the configuration is not as expected.{/c}

## Running

### Backend

```bash
  mvn clean install
```
run OrdersSplitApplication.java

### Frontend

```bash
  cd webapp/src
  npm install
  node index.js
  
```