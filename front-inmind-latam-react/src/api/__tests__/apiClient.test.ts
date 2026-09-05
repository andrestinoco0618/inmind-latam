import apiClient from '../apiClient';

describe('apiClient', () => {
  it('should have correct base URL', () => {
    expect(apiClient.defaults.baseURL).toBe(process.env.NEXT_PUBLIC_HOST+'/api/v1/transaction/questionnaire');
  });

  it('should have correct headers', () => {
    expect(apiClient.defaults.headers['Content-Type']).toBe('application/json');
  });

  it('should have response interceptor', () => {
    expect(apiClient.interceptors.response).toBeDefined();
  });
}); 