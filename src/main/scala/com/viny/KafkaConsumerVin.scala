package com.viny

import java.time.Duration
import java.util.Properties
import scala.collection.JavaConverters._
import com.example.Customer
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer

object KafkaConsumerVin {
  def main(args: Array[String]): Unit = {

    val props = new Properties()
    props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092")
    props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[KafkaAvroDeserializer].getName)
    props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false")
    props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG ,"earliest")
    props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "vin-grp2")
    props.setProperty("schema.registry.url", "http://127.0.0.1:8081")
    props.setProperty("specific.avro.reader", "true")

    val consumer = new KafkaConsumer[String, Customer](props)
    consumer.subscribe(List("viny").asJava)

    while(true){
      val records = consumer.poll(Duration.ofSeconds(1))

      records.asScala.foreach(record => {
        val customer:Customer = record.value()
        println(customer)
      })
      consumer.commitSync()
    }
  }
}
