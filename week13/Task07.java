public class Task07 {
    interface DeliveryChannelStrategy {
        boolean send(String contact, String message);
    }

    static class EmailDelivery implements DeliveryChannelStrategy {
        @Override
        public boolean send(String contact, String message) {
            System.out.println("EmailDelivery: sending email to " + contact + " -> " + message);
            return true;
        }
    }

    static class SmsDelivery implements DeliveryChannelStrategy {
        @Override
        public boolean send(String contact, String message) {
            System.out.println("SmsDelivery: sending SMS to " + contact + " -> " + message);
            return true;
        }
    }

    static class PushNotificationDelivery implements DeliveryChannelStrategy {
        @Override
        public boolean send(String contact, String message) {
            System.out.println("PushNotificationDelivery: pushing to device " + contact + " -> " + message);
            return true;
        }
    }

    static class UserPreferenceStore {
        public String resolveContact(String userId) {
            System.out.println("UserPreferenceStore: resolved contact for " + userId);
            return userId + "@example.com";
        }
    }

    static class TemplateFormatter {
        public String render(String messageKey, String payload) {
            System.out.println("TemplateFormatter: rendering template " + messageKey);
            return "[" + messageKey + "] " + payload;
        }
    }

    static class DeliveryLogger {
        public void log(String userId, boolean success) {
            System.out.println("DeliveryLogger: user=" + userId + " status=" + (success ? "DELIVERED" : "FAILED"));
        }
    }

    static class NotificationFacade {
        private final UserPreferenceStore preferenceStore = new UserPreferenceStore();
        private final TemplateFormatter formatter = new TemplateFormatter();
        private final DeliveryLogger logger = new DeliveryLogger();
        private DeliveryChannelStrategy strategy;

        public NotificationFacade(DeliveryChannelStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(DeliveryChannelStrategy strategy) {
            this.strategy = strategy;
        }

        public void sendAlert(String userId, String messageKey, String payload) {
            String contact = preferenceStore.resolveContact(userId);
            String rendered = formatter.render(messageKey, payload);
            boolean ok = strategy.send(contact, rendered);
            logger.log(userId, ok);
        }
    }

    public static void main(String[] args) {
        NotificationFacade notifier = new NotificationFacade(new EmailDelivery());
        notifier.sendAlert("user01", "LOGIN_ALERT", "New sign-in detected");
        notifier.setStrategy(new SmsDelivery());
        notifier.sendAlert("user01", "OTP_CODE", "Code: 482910");
        notifier.setStrategy(new PushNotificationDelivery());
        notifier.sendAlert("user01", "ORDER_SHIPPED", "Order #778 is on the way");
    }
}
