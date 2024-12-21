package com.devbank.currency.gold.impl.mongo;

import com.devbank.currency.gold.api.DTO.CurrencyRateDTO;
import com.devbank.currency.gold.api.enums.CurrencyCode;
import com.devbank.currency.gold.impl.mongo.document.CurrencyRateDocument;
import com.devbank.currency.gold.impl.mongo.mapper.CurrencyRateMapper;
import com.devbank.currency.gold.impl.mongo.repository.CurrencyRateRepository;
import com.devbank.currency.gold.impl.mongo.service.CurrencyRateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CurrencyRateServiceImplTest {


}
