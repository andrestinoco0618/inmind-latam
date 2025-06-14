# InMindLatam

This project is a web application developed with Next.js and TypeScript that implements an interactive psychological form with different types of questions and validations.

## 🚀 Features

- Interactive psychological form
- Multiple question types:
  - Open-ended questions
  - Closed questions
  - Multiple choice questions
  - Informative questions
- Required field validations
- WhatsApp integration
- Responsive design
- Smooth animations and transitions

## 🛠️ Technologies

- Next.js 14.2.5
- React 18
- TypeScript
- Axios for HTTP requests
- FontAwesome for icons
- React Transition Group for animations
- Normalize.css for base styles

## 📁 Project Structure

```
src/
├── api/          # API endpoints and services
├── app/          # Application pages and routes
├── components/   # Reusable components
│   ├── ButtonWhatsApp/      # WhatsApp contact button
│   ├── Footer/              # Footer
│   ├── Header/              # Header
│   ├── InformativeQuestions/# Informative questions
│   ├── MandatoryAlert/      # Required field alerts
│   ├── Modals/             # Modal components
│   ├── MultipleCloseQuestions/ # Multiple choice questions
│   ├── MultipleOpenQuestions/  # Multiple open questions
│   ├── OpenQuestions/      # Open-ended questions
│   ├── PsychologicalForm/  # Main form
│   ├── SelectQuestions/    # Selection questions
│   ├── SimpleCloseQuestion/# Single choice questions
│   ├── TermsConditionsAcceptance/ # Terms acceptance
│   └── Title/             # Title components
├── hooks/        # Custom hooks
├── styles/       # Global styles
└── utils/        # Utilities and helpers
```

## 🚀 Installation

1. Clone the repository
2. Install dependencies:
```bash
npm install
```

## 🏃‍♂️ Development

To start the development server:
```bash
npm run dev
```

## 🏗️ Build

To build the application for production:
```bash
npm run build
```

## 🐳 Docker

The project includes Docker configuration for easy deployment:

```bash
# Build the image
docker build -t frontend-test .

# Run the container
docker run -p 3000:3000 frontend-test
```

## 📝 Available Scripts

- `npm run dev`: Starts the development server
- `npm run build`: Builds the application for production
- `npm run start`: Starts the application in production mode
- `npm run lint`: Runs the linter

## 🔍 Main Components

### PsychologicalForm
Main component that handles the complete psychological form, including question navigation logic and answer validation.

### Question Types
- **OpenQuestions**: Open-ended questions
- **SimpleCloseQuestion**: Single choice questions
- **MultipleCloseQuestions**: Multiple choice questions
- **MultipleOpenQuestions**: Multiple open-ended questions
- **InformativeQuestions**: Informative questions without required answers

### UI Components
- **Header**: Application header
- **Footer**: Footer
- **ButtonWhatsApp**: WhatsApp contact button
- **MandatoryAlert**: Required field alerts
- **Modals**: Modal windows for interactions

## 🔐 Validations

The system includes validations for:
- Required fields
- Answer format
- Terms and conditions
- Question navigation

## 🎨 Styles

The project uses:
- CSS Modules for component-specific styles
- Normalize.css for style reset
- Responsive design
- Animations and transitions for better UX

## 📱 Responsive Design

The application is designed to work on:
- Mobile devices
- Tablets
- Desktop

## 🔄 Workflow

1. User starts the form
2. Navigates through questions
3. Completes required answers
4. Accepts terms and conditions
5. Submits the form
6. WhatsApp contact option
