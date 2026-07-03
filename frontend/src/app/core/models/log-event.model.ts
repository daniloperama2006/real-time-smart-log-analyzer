export interface LogEvent {
  timestamp: string;

  serviceName: string;

  statusCode: number;

  responseTime: number;

  message: string;

  scenario: string;
}
