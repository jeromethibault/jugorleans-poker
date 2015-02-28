package fr.jugorleans.poker.server.spec.test;

import fr.jugorleans.poker.server.spec.Specification;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.spec.Specification}
 */
public class SpecificationTest {

    private final Specification<Void> trueSpecification = t -> true;

    private final Specification<Void> falseSpecification = t -> false;

    @Test
    public void testIsSatisfiedBy() {
        Assert.assertTrue(this.trueSpecification.isSatisfiedBy(null));
        Assert.assertFalse(this.falseSpecification.isSatisfiedBy(null));
    }

    @Test
    public void testAnd() {
        Assert.assertTrue(this.trueSpecification.and(this.trueSpecification).isSatisfiedBy(null));
        Assert.assertFalse(this.trueSpecification.and(this.falseSpecification).isSatisfiedBy(null));
        Assert.assertFalse(this.falseSpecification.and(this.trueSpecification).isSatisfiedBy(null));
        Assert.assertFalse(this.falseSpecification.and(this.falseSpecification).isSatisfiedBy(null));
    }

    @Test
    public void testNegate() {
        Assert.assertFalse(this.trueSpecification.negate().isSatisfiedBy(null));
        Assert.assertTrue(this.falseSpecification.negate().isSatisfiedBy(null));
    }

    @Test
    public void testOr() {
        Assert.assertTrue(this.trueSpecification.or(this.trueSpecification).isSatisfiedBy(null));
        Assert.assertTrue(this.trueSpecification.or(this.falseSpecification).isSatisfiedBy(null));
        Assert.assertTrue(this.falseSpecification.or(this.trueSpecification).isSatisfiedBy(null));
        Assert.assertFalse(this.falseSpecification.or(this.falseSpecification).isSatisfiedBy(null));
    }

    @Test
    public void testXor() {
        Assert.assertFalse(this.trueSpecification.xor(this.trueSpecification).isSatisfiedBy(null));
        Assert.assertTrue(this.trueSpecification.xor(this.falseSpecification).isSatisfiedBy(null));
        Assert.assertTrue(this.falseSpecification.xor(this.trueSpecification).isSatisfiedBy(null));
        Assert.assertFalse(this.falseSpecification.xor(this.falseSpecification).isSatisfiedBy(null));
    }
}
