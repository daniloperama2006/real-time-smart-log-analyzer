import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LogEvent } from '../models/log-event.model';

@Injectable({
  providedIn: 'root',
})
export class LogStreamService {
  connect(): Observable<LogEvent> {
    return new Observable((observer) => {
      const eventSource = new EventSource('http://localhost:8080/api/v1/logs/stream');

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
