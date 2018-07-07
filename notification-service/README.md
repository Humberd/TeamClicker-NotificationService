# Team Clicker Notification Service

## Deployment requirements

### Files

 1. _src/**main**/resources/jwt_public_key.der_ - **public** jwt validation key

How to generate keys -> https://stackoverflow.com/a/19387517/4256929

### Environment variables

 1. _TC_KAFKA_URL_ - Kafka url, for example: `192.168.99.100:32769`
 2. _TC_MAIL_USERNAME_ - Gmail Username
 3. _TC_MAIL_PASSWORD_ - Gmail Password

## Deployment Testing requirements

### Files

 1. _src/**test**/resources/jwt_public_key.der_ - **public** jwt validation key

How to generate keys -> https://stackoverflow.com/a/19387517/4256929

### Environment variables

 1. _TC_KAFKA_URL_ - Kafka url, for example: `192.168.99.100:32769`
 2. _TC_MAIL_USERNAME_ - Gmail Username
 3. _TC_MAIL_PASSWORD_ - Gmail Password