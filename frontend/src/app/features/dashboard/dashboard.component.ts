import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LogEvent } from '../../core/models/log-event.model';
import { IncidentEvent } from '../../core/models/incident-event.model';
import { AIIncidentReport } from '../../core/models/ai-incident-report.model';

import { LogStreamService } from '../../core/services/log-stream.service';
import { IncidentStreamService } from '../../core/services/incident-stream.service';
import { AIStreamService } from '../../core/services/ai-stream.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent implements OnInit {
  logs = signal<LogEvent[]>([]);

  incidents = signal<IncidentEvent[]>([]);

  aiReports = signal<AIIncidentReport[]>([]);

  totalLogs = signal(0);

  criticalIncidents = signal(0);

  constructor(
    private logStreamService: LogStreamService,
    private incidentStreamService: IncidentStreamService,
    private aiStreamService: AIStreamService,
  ) {}

  ngOnInit(): void {
    this.connectLogs();

    this.connectIncidents();

    this.connectAIReports();
  }

  private connectLogs(): void {
    this.logStreamService.connect().subscribe((log) => {
      this.logs.update((logs) => [log, ...logs.slice(0, 99)]);

      this.totalLogs.update((v) => v + 1);
    });
  }

  private connectIncidents(): void {
    this.incidentStreamService.connect().subscribe((incident) => {
      this.incidents.update((items) => [incident, ...items.slice(0, 19)]);

      if (incident.severity === 'CRITICAL' || incident.severity === 'HIGH') {
        this.criticalIncidents.update((v) => v + 1);
      }
    });
  }

  private connectAIReports(): void {
    this.aiStreamService.connect().subscribe((report) => {
      this.aiReports.update((items) => [report, ...items.slice(0, 9)]);
    });
  }

  trackByTimestamp(_: number, item: any): string {
    return item.timestamp;
  }
}
