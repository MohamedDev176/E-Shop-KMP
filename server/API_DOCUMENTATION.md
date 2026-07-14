# MultiPlatform API Server

This is a Ktor-based REST API server with authentication endpoints (register, login, and forgot password) connected to MongoDB.

## Prerequisites

- Kotlin 2.4.0+
- Gradle
- MongoDB (local or remote instance)
- Java 11+

## Setup Instructions

### 1. Clone and Configure

```bash
cd server
cp .env .env
```

### 2. Update Environment Variables

Edit `.env` file with your actual values:

```env
MONGODB_URI=mongodb://localhost:27017
DATABASE_NAME=multiplatform_db
JWT_SECRET=your-very-secret-key-for-jwt
EMAIL_SENDER=your-email@gmail.com
EMAIL_PASSWORD=your-app-specific-password
```

### 3. Install MongoDB (if local)

#### Using Homebrew (macOS):
```bash
brew install mongodb-community
brew services start mongodb-community
```

#### Using Docker:
```bash
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

### 4. Build and Run

```bash
# Build the project
./gradlew build

# Run the server
./gradlew run
```

The server will start on `http://localhost:8080`

## API Endpoints

### Health Check
```
GET /health
Response: OK
```

### Register New User
```
POST /api/register
Content-Type: application/json

{
  "email": "user@example.com",
  "name": "John Doe",
  "password": "securePassword123"
}

Response (201 Created):
{
  "success": true,
  "message": "User registered successfully",
  "token": "eyJ0eXAiOiJKV1QiLCJhbGc...",
  "user": {
    "id": "507f1f77bcf86cd799439011",
    "email": "user@example.com",
    "name": "John Doe",
    "createdAt": "2024-01-15T10:30:00.123456"
  }
}
```

### Login
```
POST /api/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securePassword123"
}

Response (200 OK):
{
  "success": true,
  "message": "Login successful",
  "token": "eyJ0eXAiOiJKV1QiLCJhbGc...",
  "user": {
    "id": "507f1f77bcf86cd799439011",
    "email": "user@example.com",
    "name": "John Doe",
    "createdAt": "2024-01-15T10:30:00.123456"
  }
}
```

### Request Password Reset
```
POST /api/forgot-password
Content-Type: application/json

{
  "email": "user@example.com"
}

Response (200 OK):
{
  "success": true,
  "message": "If an account exists with this email, a password reset link will be sent"
}
```

### Reset Password (with token from email)
```
POST /api/reset-password
Content-Type: application/json

{
  "token": "reset-token-from-email",
  "newPassword": "newSecurePassword123"
}

Response (200 OK):
{
  "success": true,
  "message": "Password reset successfully"
}
```

## Database Schema

### Users Collection

```json
{
  "_id": "507f1f77bcf86cd799439011",
  "email": "user@example.com",
  "name": "John Doe",
  "passwordHash": "$2a$12$hash...",
  "createdAt": "2024-01-15T10:30:00.123456",
  "updatedAt": "2024-01-15T10:30:00.123456",
  "isActive": true,
  "resetToken": null,
  "resetTokenExpiry": null
}
```

## Security Features

- **Password Hashing**: BCrypt with 12 rounds
- **JWT Authentication**: Bearer tokens with 24-hour expiration
- **Email Verification**: Forget password uses JWT tokens and email verification
- **HTTPS**: Use in production with SSL/TLS
- **Environment Variables**: Sensitive data stored in .env (not in source code)

## Error Handling

The API returns appropriate HTTP status codes:

- `200 OK`: Successful request
- `201 Created`: Resource created successfully
- `400 Bad Request`: Invalid input
- `401 Unauthorized`: Authentication failed
- `409 Conflict`: Resource already exists
- `500 Internal Server Error`: Server error

## Testing with cURL

### Register
```bash
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "name": "Test User",
    "password": "TestPassword123"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "TestPassword123"
  }'
```

### Forgot Password
```bash
curl -X POST http://localhost:8080/api/forgot-password \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com"
  }'
```

## Email Configuration

For Gmail:
1. Enable 2-Factor Authentication
2. Create an App Password: https://myaccount.google.com/apppasswords
3. Use the 16-character password in EMAIL_PASSWORD

For other providers, update the SMTP settings in `EmailService.kt`

## Troubleshooting

### MongoDB Connection Issues
- Ensure MongoDB is running
- Check MONGODB_URI in .env
- Verify network connectivity

### Email Not Sending
- Check EMAIL_SENDER and EMAIL_PASSWORD in .env
- Enable less secure app access or use app-specific passwords
- Check firewall and SMTP port 587

### JWT Token Issues
- Ensure JWT_SECRET is set and consistent
- Tokens expire after 24 hours
- Clear invalid tokens from client

## Future Enhancements

- [ ] Email verification on registration
- [ ] Refresh tokens
- [ ] OAuth2 integration
- [ ] Rate limiting
- [ ] API key authentication
- [ ] Password complexity requirements
- [ ] Account lockout after failed attempts
- [ ] User profile endpoints
- [ ] Admin dashboard

## License

This project is part of the MultiPlatform application suite.

