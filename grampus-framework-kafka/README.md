# 配置
```yaml
spring:
  kafka:
    bootstrap-servers: "192.168.8.110:9092"
    producer:
      retries: 0
      batch-size: 16384
      buffer-memory: 33554432
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
    consumer:
      group-id: ${spring.application.name}-${spring.profiles.active}
      enable-auto-commit: false
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      properties:
        session.timeout.ms: 60000
        isolation.level: read_committed
    listener:
      log-container-config: false
      concurrency: 5
      ack-mode: manual_immediate
```