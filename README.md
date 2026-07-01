# Real-Time Smart Log & Metric Analyzer 🚀

A cloud-native reactive system that processes real-time server logs and metrics, detects anomalies, and uses AI-driven root cause analysis to suggest automated fixes.

---

## 📋 Project Overview

This project demonstrates advanced backend engineering concepts by simulating production server behavior, continuously streaming logs to a real-time dashboard, and detecting anomalies such as:

*   **Brute-force attacks** (401 unauthorized spikes)
*   **Database failures** (500 internal server error timeouts)
*   **Memory leaks** (progressive high latency)
*   **Abnormal traffic patterns**

When a critical incident is detected, an LLM analyzes the logs asynchronously and returns a structured payload containing the **severity level**, **root cause analysis**, and a **suggested code/configuration fix**.

### 🛠️ Core Concepts Demonstrated
*   **Event-Driven Architecture (EDA)** & Hot Reactive Streams.
*   **Reactive Programming** with Spring WebFlux & Project Reactor.
*   **Real-Time Streaming** using Server-Sent Events (SSE).
*   **Resilience Patterns** with Circuit Breakers.
*   **AI-Powered Incident Analysis** and automated playbooks.
*   **Observability** and live metrics pipelines.

---

## 🏗️ Architecture
