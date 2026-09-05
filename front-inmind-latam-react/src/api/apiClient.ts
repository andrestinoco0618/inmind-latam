import axios from 'axios';

/**
 * @api {axios} apiClient Axios instance for API requests
 * @apiDescription Configured Axios instance with base URL and interceptors for handling API requests
 * @apiBaseUrl {string} NEXT_PUBLIC_HOST+'/api/v1/transaction/questionnaire'
 * @apiHeaders {Object} Content-Type: application/json
 * @apiInterceptor {Function} response - Handles API response errors
 */
const apiClient = axios.create({
  baseURL: process.env.NEXT_PUBLIC_HOST+'/api/v1/transaction/questionnaire',  
  headers: {
    'Content-Type': 'application/json',
  },
});

apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API Error:', error);
    return Promise.reject(error);
  }
);

export default apiClient;