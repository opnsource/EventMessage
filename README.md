# EventMessage for you

## Note:

### 1、Registering Custom Event method

      EventPublisher.addSupportMethod("helloTest");

### 2、Register listens for events

      EventPublisher.register(test1);

###  3、Registering Custom Event process

      EventPublisher.addEventProcess(new TestProcess());

### 4、Executive Event post

      EventPublisher.post(EventPostBuilder.createAsyPost().params("hello"));

### More to see demo project



## Flow chart for EventMessage

![](https://raw.githubusercontent.com/opnsource/EventMessage/master/doc/chart.png)


