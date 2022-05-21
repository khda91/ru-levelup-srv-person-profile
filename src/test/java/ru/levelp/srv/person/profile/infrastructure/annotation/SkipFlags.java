package ru.levelp.srv.person.profile.infrastructure.annotation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SkipFlags {

    public static final String SKIP_UNIT_TESTS = "skipUnitTests";

    public static final String SKIP_COMPONENT_TESTS = "skipComponentTests";

    public static final String SKIP_API_TESTS = "skipApiTests";

    public static final String SKIP_CONTRACT_TESTS = "skipContractTests";
}
