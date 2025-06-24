/*
 * package vn.leoo.common.data;
 * 
 * import java.util.ArrayList; import java.util.List;
 * 
 * import org.springframework.data.jpa.domain.Specification;
 * 
 * import jakarta.persistence.criteria.CriteriaBuilder; import
 * jakarta.persistence.criteria.CriteriaQuery; import
 * jakarta.persistence.criteria.Predicate; import
 * jakarta.persistence.criteria.Root;
 * 
 * 
 * @SuppressWarnings("serial") public class BaseSpecification<T> implements
 * Specification<T> { private List<SearchCriteria> list;
 * 
 * public BaseSpecification() { this.list = new ArrayList<>(); }
 * 
 * public void add(SearchCriteria criteria) { list.add(criteria); }
 * 
 * @Override public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
 * CriteriaBuilder builder) { //Create a new predicate list List<Predicate>
 * predicates = new ArrayList<>();
 * 
 * //Add criteria to predicates for (SearchCriteria criteria : list) {
 * SearchOperation operation = criteria.getSearchOperation(); switch (operation)
 * { case OP_GREATER_THAN: predicates.add(builder.greaterThan(
 * root.get(criteria.getKey()), criteria.getValue().toString())); break; case
 * OP_LESS_THAN: predicates.add(builder.lessThan( root.get(criteria.getKey()),
 * criteria.getValue().toString())); break; case OP_GREATER_THAN_EQUAL:
 * predicates.add(builder.greaterThanOrEqualTo( root.get(criteria.getKey()),
 * criteria.getValue().toString())); break; case OP_LESS_THAN_EQUAL:
 * predicates.add(builder.lessThanOrEqualTo( root.get(criteria.getKey()),
 * criteria.getValue().toString())); break; case OP_NOT_EQUAL:
 * predicates.add(builder.notEqual( root.get(criteria.getKey()),
 * criteria.getValue())); break; case OP_EQUAL: predicates.add(builder.equal(
 * root.get(criteria.getKey()), criteria.getValue())); break; case OP_MATCH:
 * predicates.add(builder.like( builder.lower(root.get(criteria.getKey())), "%"
 * + criteria.getValue().toString().toLowerCase() + "%")); break; case
 * OP_MATCH_END: predicates.add(builder.like(
 * builder.lower(root.get(criteria.getKey())),
 * criteria.getValue().toString().toLowerCase() + "%")); break; case
 * OP_MATCH_START: predicates.add(builder.like(
 * builder.lower(root.get(criteria.getKey())), "%" +
 * criteria.getValue().toString().toLowerCase())); break; case OP_IN:
 * predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.
 * getValue())); break; case OP_NOT_IN:
 * predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue(
 * ))); break; default: break; } } return builder.and(predicates.toArray(new
 * Predicate[0])); } }
 */