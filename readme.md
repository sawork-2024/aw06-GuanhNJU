## 作业要求

请参考spring-petclinic-rest/spring-petclinic-microserivces 将webpos项目改为微服务架构，具体要求包括：

1. 至少包含独立的产品管理服务、订单管理服务以及discovery/gateway等微服务架构下需要的基础设施服务；
2. 请将系统内的不同微服务实现不同的计算复杂度，通过压力测试实验验证对单个微服务进行水平扩展（而无需整个系统所有服务都进行水平扩展）可以提升系统性能，请给出实验报告；
3. 请使用`RestTemplate`进行服务间访问，验证Client-side LB可行；
4. 请注意使用断路器等机制；
5. 如有兴趣可在kubernetes或者minikube上进行部署。

请编写readme对自己的系统和实验进行详细介绍

## 系统架构

本系统是基于SpringCloud开发，采用微服务架构，分别实现了以下服务：

- 注册中心/服务发现： 即本系统中的cloud-register模块，负责服务发现与注册。使用Spring Cloud Netflix Eureka，将系统中的服务注册到服务发现服务器上，并从注册中心被其他服务找到。
- 产品管理：即本系统中的 cloud-product、cloud-product-copy和cloud-product2模块，向客户提供了查看产品列表和选择指定产品的服务。cloud-product-copy和cloud-product在服务发现器上有相同的服务名，水平扩展了产品管理服务模块，并能够进行负载均衡。cloud-product2模块测试了模块间相互调用服务的功能，能够调用cloud-product提供的服务中的查看产品列表服务。
- 订单管理：即本系统中的cloud-cart模块，该模块提供了订单的存储和管理服务，能够将前端提交的订单进行处理并更新后端数据库。
- 网关/Gateway：即本系统中的cloud-gateway模块，该模块提供了一个简单、有效的方式来路由API，并为微服务架构提供了一种简单的、基于过滤器的API网关服务。

## 性能测试

本实验对产品管理模块的查看产品列表服务进行了压力测试，当只有一个产品管理服务运行时，1000次请求需要447秒，在增加了一个产品管理模块后，只需253秒就能运行完成，可见对单个微服务进行水平扩展可以提升系统性能。随着服务数的增加，运行时间并没有线性减少，可能是因为redis和服务器的性能成为了提升系统性能的瓶颈。

## 负载均衡

cloud-product-copy和cloud-product在服务发现器上有相同的服务名，能够进行负载均衡。经过实验测试，使用cloud-product2模块调用100次服务名为product1的服务时，二者（cloud-product-copy和cloud-product）被调用次数相等。

![Eureka](D:\work\sawork\aw06-main\Eureka.PNG)

## 断路器

在使用cloud-product2调用product1的服务时，采用了Spring Cloud Netflix Hystrix断路器，Hystrix提供了功能，防止系统雪崩，提高了系统的稳定性。当product1服务超时或不可用时，断路器能够自动断开对该服务的调用，从而保护系统的其他部分不受影响。