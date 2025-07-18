# 💳 Stripe Payment Service – Java Microservice

**Seamlessly handle subscriptions, one-time purchases, and webhook events using Stripe’s Java SDK.**  
Built with **Spring Boot**, **Stripe**, and **Docker** — this microservice is designed for modern event-driven systems.

---

## 🚀 What Does This Service Do?

This microservice integrates with **Stripe's Java SDK** to offer:

- ✅ **Subscription billing** (Basic / Pro / Enterprise)
- 🛒 **One-time product purchases**
- 📤 **Webhook processing** for payment and subscription lifecycle events
- 📦 Clean REST APIs + optional Kafka integration for system-wide event publishing

---

## 💡 Use Cases

- 🧾 User subscribes to a SaaS plan (monthly/yearly)
- 🛍️ User buys a digital product or upgrade
- 🔄 Stripe webhook triggers status update or downstream actions

---

## 🔧 How it Works

1. A frontend or API client calls the `/subscribe` or `/checkout` endpoint.
2. The service creates a Stripe session and returns a redirectable `checkoutUrl`.
3. Stripe handles the billing flow.
4. After payment, Stripe calls the service’s **webhook** endpoint.
5. Webhook logic validates the event, updates internal records, and **publishes the event to Kafka** (if enabled).

---

## 🔐 Supported Payment Operations

| Endpoint                | Method | Description                             |
|------------------------|--------|-----------------------------------------|
| `/api/payment/subscribe` | POST   | Subscribe user to Basic/Pro/Enterprise  |
| `/api/payment/stripe/subscription`  | POST   | One-time product checkout               |
| `/api/payment/stripe/webhook`   | POST   | Handles Stripe event callbacks          |

---

## 🔂 Webhooks Handled

The service listens to and processes the following **Stripe webhook events**:

| Stripe Event                  | Purpose                                           |
|------------------------------|---------------------------------------------------|
| `checkout.session.completed` | Trigger post-purchase flow (activate feature)     |
| `invoice.paid`               | Confirm successful recurring payment              |
| `customer.subscription.deleted` | Cancel subscription and notify user         |
| `payment_intent.failed`      | Handle failed payment notification                |

You can extend this easily to support more Stripe events as needed.

---

## ⚙️ Tech Stack

- **Java 17**
- **Spring Boot**
- **Stripe Java SDK**
- **Docker & Docker Compose**
- **Kafka (optional)**
- **JUnit + MockMVC**

---

## 📦 Maven Dependency

```xml
<dependency>
  <groupId>com.stripe</groupId>
  <artifactId>stripe-java</artifactId>
  <version>24.0.0</version>
</dependency>
```

## 🐳 Run with Docker

```bash
docker-compose up --build
```

This spins up:
- Payment Service (payment-service)
-	PostgreSQL or H2 (optional)
-	Stripe CLI (optional, for local webhook testing)

## 📁 .env Configuration (Sample)

```env
# Stripe API
STRIPE_API_KEY=sk_test_XXXXXXXXXXXXXXXXXXXXXXXX
STRIPE_WEBHOOK_SECRET=whsec_XXXXXXXXXXXXXXXXXX

# Plans
STRIPE_PLAN_BASIC=price_abc123
STRIPE_PLAN_PRO=price_def456
STRIPE_PLAN_ENTERPRISE=price_ghi789

# One-time product
STRIPE_PRODUCT_ID=prod_xyz999
```

## 🛠️ Extending This Service
-	✅ Add support for coupons, metered billing, trials
-	✅ Store Stripe session & customer IDs in DB
-	✅ Add retry & deduplication logic for webhooks
-	✅ Send Kafka events to downstream billing or user services
-	✅ Integrate with SendGrid or email services for receipts
-	✅ Add Kafka event publishing (e.g. subscription.created)


## ✅ Designed For
-	Microservice-first architectures
-	SaaS or marketplaces using Stripe
-	Teams who want clean separation between app logic and billing logic
-	Systems that use Kafka or async flows for user state sync

## 📚 Helpful Links
-	Stripe Java SDK
-	Stripe Webhook Docs
-	Stripe Checkout Sessions
-	Spring Boot Reference

“Simple, scalable, and Stripe-ready. Drop it in — let it handle the payments.”
