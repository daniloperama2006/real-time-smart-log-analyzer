import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IncidentEvent } from '../models/incident-event.model';

@Injectable({
  providedIn: 'root',
})
export class IncidentStreamService {
  connect(): Observable<IncidentEvent> {
    return new Observable((observer) => {
      const eventSource = new EventSource('http://localhost:8080/api/v1/incidents/stream');

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
