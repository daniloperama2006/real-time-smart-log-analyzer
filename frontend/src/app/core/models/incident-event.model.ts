export interface IncidentEvent {
  timestamp: string;

  incidentType: string;

  severity: string;

  description: string;

  occurrences: number;
}
