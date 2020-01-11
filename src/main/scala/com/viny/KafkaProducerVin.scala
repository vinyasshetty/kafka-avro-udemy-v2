package com.viny

import java.util.Properties

import com.example.Customer
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerConfig, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.serialization.StringSerializer

object KafkaProducerVin {
  def main(args: Array[String]): Unit = {

    val props = new Properties()
    props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092")
    props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,classOf[StringSerializer].getName)
    props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer].getName)
    props.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true")
    props.setProperty("schema.registry.url", "http://127.0.0.1:8081")

    val customer = Customer.newBuilder().setFirstName("Ballu")
      .setLastName("rao").setAge(667)
      .setHeight(160f).setWeight(75.2f).setPhone("8888888").build()

    val producer = new KafkaProducer[String, Customer](props)

    val record = new ProducerRecord[String,Customer]("viny",customer)

    producer.send(record, new Callback {
      override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
        if(exception == null){
          println(
            f"""Msg sent : Offset ${metadata.offset()},
               | Partition is ${metadata.partition()}
               |""".stripMargin)
        }
        else{
          throw exception
        }
      }
    })
    producer.flush()
    producer.close()

  }
}
