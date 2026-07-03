import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AIIncidentReport } from '../models/ai-incident-report.model';

@Injectable({
  providedIn: 'root',
})
export class AIStreamService {
  connect(): Observable<AIIncidentReport> {
    return new Observable((observer) => {
      const eventSource = new EventSource('http://localhost:8080/api/v1/incidents/ai-stream');

      eventSource.onmessage = (event) => {
        observer.next(JSON.parse(event.data));
      };

      eventSource.onerror = (error) => {
        observer.error(error);

        eventSource.close();
      };

      return () => eventSource.close();
    });
  }
}
