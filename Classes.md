- [x] FacetType<enum>
- [x] ResourceType<enum>

- [x] Operation

- [X] Facet<FacetType, count>
- [X] Player<ResourceInventory, MovementCount, FacetInventory, Dice<[6 facet]>[2]>
- [X] TempPlayer<Player, Facets, Operations>

- [x] Rule
- [x] FacetRule<Rule>
- [x] RuleSet<FacetRule[]>
- [x] RuleFactory -> resolveRuleSet(playerCount) => RuleSet

- [x] FacetRuleManager -> resolve(Facet[]) => Operation[]

- [X] Game<Player[], RuleSet, FacetRuleManager>

## P2

- [ ] CardType<enum>
- [ ] Player += `<CardInventory>`
- [ ] CardRule<Rule>
- [ ] GameRule<Rule>
- [ ] RuleSet += `<CardRule[], GameRule[]>`
