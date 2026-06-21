# Test Map

This document maps each design pattern example to the tests that prove its
behavior.

| Pattern | Test class | What the tests prove |
| --- | --- | --- |
| Strategy | `CheckoutServiceTest` | Discount rules can change without changing checkout flow |
| Factory | `NotifierFactoryTest` | Channels resolve to the expected notifier implementation |
| Builder | `ApiRequestTest` | Required fields, defaults, and immutability are enforced |
| Adapter | `LegacyPaymentAdapterTest` | Legacy payment responses map to domain results |
| Observer | `SubscriptionEventPublisherTest` | Subscribers receive events and can unsubscribe |
| Decorator | `ResponseRendererTest` | Response metadata can be composed without changing body rendering |
| Command | `BillingCommandDispatcherTest` | Queued billing commands execute in order |
| Chain of Responsibility | `SubscriptionApprovalChainTest` | Approval rules stop or continue the chain correctly |
| State | `SubscriptionLifecycleTest` | Subscription transitions are state-specific |

## How To Use This In Interviews

When explaining a pattern, open the implementation and the test together. The
implementation shows the design decision; the test shows the behavior that made
the design useful.
