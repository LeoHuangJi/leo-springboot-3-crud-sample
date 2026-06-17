This Spring Boot project implements a simple CRUD system for Tutorial entities using JPA and QueryDSL.
It supports dynamic search with multiple conditions and pagination using QueryDSL.
The project is well-structured with clear separation of layers: Entity, DTO, Repository, DAO, Service, and Controller.
It also supports calling stored procedures in the database using StoredProcedureQuery.

Audit Log Module Overview Purpose Records the history of business actions (create, approve, issue number, update, delete) across the system, enabling traceability, history lookup, and compliance auditing.
Business Service
    │
    ├── Executes business logic (save/update/delete entity)
    │
    └── AuditLogContextBuilder
            │
            ├── newLog(module, action, rootType, rootId)
            │       → auto-resolves actor (username, role, ip, userAgent...)
            │       → auto-resolves traceId (from MDC, set by TraceIdFilter)
            │
            ├── addInsert / addUpdate / addDelete / addList
            │       → computes diff between old and new (only changed fields)
            │
            └── build() → AuditLogContext (DTO)
                    │
                    └── AuditEventPublisher.publish(context)
                            │
                            ├── Inside @Transactional → Spring Event, waits for AFTER_COMMIT
                            ├── Outside @Transactional (Controller, batch) → saved directly, async
                            └── (optional) Kafka — for distributed systems
                                    │
                                    └── AuditLogService (@Async on dedicated thread pool)
                                            │
                                            └── Persists audit_log + audit_log_detail
                                                    (with fallback file logging on DB failure)
