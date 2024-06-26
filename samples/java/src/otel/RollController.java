// RollController.java
package otel;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Scope;

@RestController
public class RollController {
  private static final Logger logger = LoggerFactory.getLogger(RollController.class);
  private final Tracer tracer;

  @Autowired
  RollController(OpenTelemetry openTelemetry) {
    this.tracer = openTelemetry.getTracer(RollController.class.getName(), "0.1.0");
  }

  @GetMapping("/rolldice")
  public List<Integer> index(@RequestParam("player") Optional<String> player,
      @RequestParam("rolls") Optional<Integer> rolls) {
    Span span = tracer.spanBuilder("rollTheDice").startSpan();
    try (Scope scope = span.makeCurrent()) {
      if (!rolls.isPresent()) {
        rolls = Optional.of(1);
      }
      List<Integer> result = new Dice(1, 6).rollTheDice(rolls.get());
  
      if (player.isPresent()) {
        logger.info("{} is rolling the dice: {}", player.get(), result);
      } else {
        logger.info("Anonymous player is rolling the dice: {}", result);
      }
      return result;
    } catch(Throwable t) {
      span.recordException(t);
      throw t;
    } finally {
      span.end();
    }
    
  }
}
