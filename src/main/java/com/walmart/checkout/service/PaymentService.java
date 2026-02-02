package com.walmart.checkout.service;

import com.walmart.checkout.model.PaymentMethod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class PaymentService {
    
    private final Random random = new Random();
    
    public String processPayment(BigDecimal amount, PaymentMethod paymentMethod) {
        // Simular procesamiento de pago
        try {
            // Simular tiempo de procesamiento
            Thread.sleep(100);
            
            // Simular éxito/falla (95% tasa de éxito)
            boolean success = random.nextDouble() < 0.95;
            
            if (success) {
                return "CONFIRMED";
            } else {
                return "FAILED";
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "FAILED";
        }
    }
    
    public boolean validatePaymentMethod(PaymentMethod paymentMethod) {
        // Validación básica - en el mundo real se validarían detalles de tarjeta, etc.
        return paymentMethod != null;
    }
    
    public String getPaymentProcessorName(PaymentMethod paymentMethod) {
        switch (paymentMethod) {
            case CREDIT_CARD:
                return "Visa/MasterCard Processor";
            case DEBIT:
                return "Bank Debit Processor";
            case CASH:
                return "Cash Register";
            case DIGITAL_WALLET:
                return "PayPal/Apple Pay";
            case BANK_TRANSFER:
                return "ACH Processor";
            default:
                return "Unknown Processor";
        }
    }
}